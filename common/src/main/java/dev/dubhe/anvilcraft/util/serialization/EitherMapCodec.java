package dev.dubhe.anvilcraft.util.serialization;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;

import java.util.Optional;
import java.util.stream.Stream;

public class EitherMapCodec<L, R> extends MapCodec<Either<L, R>> {
    private final MapCodec<L> left;
    private final MapCodec<R> right;

    public EitherMapCodec(MapCodec<L> left, MapCodec<R> right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public <T> Stream<T> keys(DynamicOps<T> ops) {
        return Stream.concat(left.keys(ops), right.keys(ops));
    }

    @Override
    public <T> DataResult<Either<L, R>> decode(DynamicOps<T> ops, MapLike<T> input) {
        DataResult<Either<L, R>> left = this.left.decode(ops, input).map(Either::left);
        DataResult<Either<L, R>> right = this.right.decode(ops, input).map(Either::right);
        Optional<Either<L, R>> leftOptional = left.result();
        Optional<Either<L, R>> rightOptional = right.result();
        if (leftOptional.isPresent() && rightOptional.isPresent()) {
            return DataResult.error(() -> "Both alternatives read successfully, can not pick the correct one; first: "
                    + leftOptional.get()
                    + " second: "
                    + rightOptional.get(),
                leftOptional.get()
            );
        }
        if (leftOptional.isPresent()) {
            return left;
        }
        if (rightOptional.isPresent()) {
            return right;
        }
        return left.apply2((lrEither, o) -> o, right);
    }

    @Override
    public <T> RecordBuilder<T> encode(Either<L, R> input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
        return input.map(it -> left.encode(it, ops, prefix), it -> right.encode(it, ops, prefix));
    }

    public static <L, R> MapCodec<Either<L, R>> either(MapCodec<L> leftCodec, MapCodec<R> rightCodec) {
        return new EitherMapCodec<>(leftCodec, rightCodec);
    }
}
