package io.github.qprove_p.codesnippetstash.storage;

import lombok.Getter;

@Getter
public class DBConnector {

    private final String URL = "jdbc:sqlite:snippets.db";
}
