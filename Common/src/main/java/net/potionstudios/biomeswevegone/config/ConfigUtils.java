package net.potionstudios.biomeswevegone.config;

public class ConfigUtils {

	public record CommentValue<T>(String comment, T value) {
		public static <T> CommentValue<T> of(String comment, T value) {
			return new CommentValue<>(comment, value);
		}
	}
}
