package net.potionstudios.biomeswevegone.math;

import com.mojang.serialization.Codec;

import static java.lang.Math.*;

public enum BlendingFunction {

    EASE_IN_OUT_CIRC {
        @Override
        public double apply(double x, double... params) {
            return x < 0.5
                    ? (1 - sqrt(1 - pow(2 * x, 2))) / 2
                    : (sqrt(1 - pow(-2 * x + 2, 2)) + 1) / 2;
        }
    },

    EASE_OUT_CUBIC {
        @Override
        public double apply(double x, double... params) {
            return 1 - pow(1 - x, 3);
        }
    },

    EASE_OUT_BOUNCE {
        @Override
        public double apply(double x, double... params) {
            double n1 = 7.5625;
            double d1 = 2.75;
            double t = x;

            if (t < 1 / d1) {
                return n1 * t * t;
            } else if (t < 2 / d1) {
                return n1 * (t -= 1.5 / d1) * t + 0.75;
            } else if (t < 2.5 / d1) {
                return n1 * (t -= 2.25 / d1) * t + 0.9375;
            } else {
                return n1 * (t -= 2.625 / d1) * t + 0.984375;
            }
        }
    },

    EASE_OUT_ELASTIC {
        @Override
        public double apply(double x, double... params) {
            double intensity = params.length > 0 ? params[0] : 10.0;
            double c4 = (2 * Math.PI) / 3;

            return x == 0
                    ? 0
                    : x == 1
                      ? 1
                      : pow(2, -intensity * x) * sin((x * 10 - 0.75) * c4) + 1;
        }
    },

    EASE_IN_CIRC {
        @Override
        public double apply(double x, double... params) {
            double exponent = params.length > 0 ? params[0] : 0.8; // TODO: ADD A CONFIG INSTEAD OF DEFAULTING TO 0.8. ONLY USED BY THE ROUNDED DRIPSTONE ROCK FEATURE.
            return 1 - sqrt(1 - pow(x, exponent));
        }
    },

    EASE_OUT_QUINT {
        @Override
        public double apply(double x, double... params) {
            return 1 - pow(1 - x, 5);
        }
    };

    public static final Codec<BlendingFunction> CODEC = Codec.STRING.xmap(s -> BlendingFunction.valueOf(s.toUpperCase()), BlendingFunction::name);


    public abstract double apply(double x, double... params);

    public double apply(double factor, double min, double max) {
        double range = max - min;
        return min + (range * apply(factor));
    }
}