package SocketServer.Utility;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Myles Megyesi
 */
public class Logging {

    public static Logger getLoggerAndSetLevel(String name, Level level) {
        Logger logger = Logger.getLogger(name);
        logger.setLevel(level);
        return logger;
    }

}
