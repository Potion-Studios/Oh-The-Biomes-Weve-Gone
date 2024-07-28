package net.potionstudios.biomeswevegone.util;

import org.joml.Vector2d;
import org.joml.Vector4d;

public class MathUtil {

    private static final Vector2d s = new Vector2d(1.0, 1.7320508); // sqrt(3)


    // xy - offset from nearest hex center
    // zw - unique ID of hexagon
    public static Vector4d calcHexInfo(Vector2d pos, double r) {
        Vector4d uv4 = new Vector4d(pos.x, pos.y, pos.x - 0.5f * r, pos.y - r);

        Vector4d hexCenter = new Vector4d(uv4).div(new Vector4d(s.x * r, s.y * r, s.x * r, s.y * r)).round();

        Vector4d offset = new Vector4d(
                pos.x - hexCenter.x * s.x * r,
                pos.y - hexCenter.y * s.y * r,
                pos.x - (hexCenter.z + 0.5f) * s.x * r,
                pos.y - (hexCenter.w + 0.5f) * s.y * r
        );

        double offsetXYDot = new Vector2d(offset.x, offset.y).dot(new Vector2d(offset.x, offset.y));
        double offsetZWDot = new Vector2d(offset.z, offset.w).dot(new Vector2d(offset.z, offset.w));

        return offsetXYDot <= offsetZWDot ?
                new Vector4d(offset.x, offset.y, hexCenter.x, hexCenter.y) :
                new Vector4d(offset.z, offset.w, hexCenter.z, hexCenter.w);
    }

    public static Vector4d[] getSurroundingHexPositions(Vector2d pos, double r) {
        Vector4d centerHexInfo = calcHexInfo(pos, r);
        Vector2d centerHex = new Vector2d(centerHexInfo.z, centerHexInfo.w);

        Vector2d[] directions = {
                new Vector2d(1, 0),
                new Vector2d(1, -1),
                new Vector2d(0, -1),
                new Vector2d(-1, 0),
                new Vector2d(-1, 1),
                new Vector2d(0, 1)
        };

        Vector4d[] neighbors = new Vector4d[6];
        for (int i = 0; i < 6; i++) {
            Vector2d neighborHex = centerHex.add(directions[i]);
            Vector2d neighborPos = new Vector2d(
                    neighborHex.x * s.x * r,
                    neighborHex.y * s.y * r
            );
            neighbors[i] = new Vector4d(neighborPos, neighborHex.x, neighborHex.y);
        }
        return neighbors;
    }
}
