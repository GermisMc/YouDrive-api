package by.youdrive.domain;

import by.youdrive.exceptions.YouDriveException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jetbrains.annotations.Contract;

import java.util.UUID;

public class ID {

    private UUID uuid;

    private ID(UUID uuid) {
        this.uuid = uuid;
    }

    @Contract("null -> null")
    public static ID from(String id) {
        if (id == null) {
            return null;
        }

        try {
            return new ID(UUID.fromString(id));
        }
        catch (IllegalArgumentException ignored) {
            throw new YouDriveException(YouDriveException.WrongArgument, "Invalid ID: " + id);
        }
    }

    @Contract(" -> !null")
    public static ID create() {
        return new ID(UUID.randomUUID());
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
