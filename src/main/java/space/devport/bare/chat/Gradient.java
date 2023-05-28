package space.devport.bare.chat;

public record Gradient(String startingColor, String... colors) {

    public String generateGradientTag(String text) {
        return String.format("<gradient:%s%s%s>%s</gradient>", this.startingColor, this.colors.length == 0 ? "" : ":", String.join(":", this.colors), text);
    }
}
