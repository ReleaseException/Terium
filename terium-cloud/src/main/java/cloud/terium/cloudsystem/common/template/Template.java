package cloud.terium.cloudsystem.common.template;

import cloud.terium.common.templates.ITemplate;

import java.nio.file.Path;

public record Template(String name, Path path) implements ITemplate {

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Path getPath() {
        return path;
    }
}