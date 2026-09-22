package net.potionstudios.biomeswevegone.world.level.levelgen.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public interface BlendingFunction {

    Codec<BlendingFunction> CODEC = Codec.STRING.dispatch(BlendingFunction::name, BlendingFunction::mapCodecFor);

    double apply(double factor);

    default double apply(double factor, double from, double to) {
        return from + (to - from) * apply(factor);
    }

    String name();

    private static MapCodec<? extends BlendingFunction> mapCodecFor(String name) {
        return switch (name) {
            case EaseInCirc.NAME -> EaseInCirc.MAP_CODEC;
            case EaseInOutCirc.NAME -> EaseInOutCirc.MAP_CODEC;
            case EaseOutBounce.NAME -> EaseOutBounce.MAP_CODEC;
            case EaseOutCubic.NAME -> EaseOutCubic.MAP_CODEC;
            case EaseOutElastic.NAME -> EaseOutElastic.MAP_CODEC;
            case EaseOutQuint.NAME -> EaseOutQuint.MAP_CODEC;
            default -> throw new IllegalArgumentException("Unknown blending function: " + name);
        };
    }

    record EaseInCirc(double exponent) implements BlendingFunction {
        static final String NAME = "ease_in_circ";
        public static final EaseInCirc INSTANCE = new EaseInCirc(2.0);
        static final MapCodec<EaseInCirc> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Codec.DOUBLE.fieldOf("exponent").forGetter(EaseInCirc::exponent)
                ).apply(instance, EaseInCirc::new)
        );

        @Override
        public double apply(double factor) {
            return 1 - Math.sqrt(1 - Math.pow(factor, exponent));
        }

        @Override
        public String name() {
            return NAME;
        }
    }

    record EaseInOutCirc() implements BlendingFunction {
        static final String NAME = "ease_in_out_circ";
        public static final EaseInOutCirc INSTANCE = new EaseInOutCirc();
        static final MapCodec<EaseInOutCirc> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public double apply(double factor) {
            return factor < 0.5
                    ? (1 - Math.sqrt(1 - Math.pow(2 * factor, 2))) / 2
                    : (Math.sqrt(1 - Math.pow(-2 * factor + 2, 2)) + 1) / 2;
        }

        @Override
        public String name() {
            return NAME;
        }
    }

    record EaseOutBounce() implements BlendingFunction {
        static final String NAME = "ease_out_bounce";
        public static final EaseOutBounce INSTANCE = new EaseOutBounce();
        static final MapCodec<EaseOutBounce> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public double apply(double factor) {
            double n1 = 7.5625;
            double d1 = 2.75;

            if (factor < 1 / d1) {
                return n1 * factor * factor;
            } else if (factor < 2 / d1) {
                factor -= 1.5 / d1;
                return n1 * factor * factor + 0.75;
            } else if (factor < 2.5 / d1) {
                factor -= 2.25 / d1;
                return n1 * factor * factor + 0.9375;
            } else {
                factor -= 2.625 / d1;
                return n1 * factor * factor + 0.984375;
            }
        }

        @Override
        public String name() {
            return NAME;
        }
    }

    record EaseOutCubic() implements BlendingFunction {
        static final String NAME = "ease_out_cubic";
        public static final EaseOutCubic INSTANCE = new EaseOutCubic();
        static final MapCodec<EaseOutCubic> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public double apply(double factor) {
            return 1 - Math.pow(1 - factor, 3);
        }

        @Override
        public String name() {
            return NAME;
        }
    }

    record EaseOutElastic(double intensity) implements BlendingFunction {
        static final String NAME = "ease_out_elastic";
        public static final EaseOutElastic INSTANCE = new EaseOutElastic(10.0);
        static final MapCodec<EaseOutElastic> MAP_CODEC = RecordCodecBuilder.mapCodec(instance ->
                instance.group(
                        Codec.DOUBLE.fieldOf("intensity").forGetter(EaseOutElastic::intensity)
                ).apply(instance, EaseOutElastic::new)
        );

        @Override
        public double apply(double factor) {
            double c4 = 2 * Math.PI / 3;

            if (factor == 0) {
                return 0;
            } else if (factor == 1) {
                return 1;
            } else {
                return Math.pow(2, -intensity * factor) * Math.sin((factor * 10 - 0.75) * c4) + 1;
            }
        }

        @Override
        public String name() {
            return NAME;
        }
    }

    record EaseOutQuint() implements BlendingFunction {
        static final String NAME = "ease_out_quint";
        public static final EaseOutQuint INSTANCE = new EaseOutQuint();
        static final MapCodec<EaseOutQuint> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public double apply(double factor) {
            return 1 - Math.pow(1 - factor, 5);
        }

        @Override
        public String name() {
            return NAME;
        }
    }
}
