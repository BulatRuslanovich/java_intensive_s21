package edu.school21.utils;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;

@Getter
@Parameters(separators = "=")
public class ArgsUtil {
    @Parameter(names = {"--enemiesCount"})
    private int enemiesCount;

    @Parameter(names = {"--wallsCount"})
    private int wallsCount;

    @Parameter(names = {"--size"})
    private int mapSize;

    @Parameter(names = {"--profile"})
    private String profile;
}
