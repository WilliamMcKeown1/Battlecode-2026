package MoveRandomly;

import battlecode.common.*;

import java.util.Random;

public class RobotPlayer {
    static int turnCount = 0;

    static final Random rng = new Random(6147);

    static final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    };

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {
        System.out.println("I'm alive");

        rc.setIndicatorString("Hello world!");

        while (true) {

            turnCount += 1;  // We have now been alive for one more turn!
            try {
                switch (rc.getType()) {
                    case SOLDIER:
                        runSoldier(rc);
                        break;
                    case MOPPER:
                        runMopper(rc);
                        break;
                    case SPLASHER:
                        break;
                    default:
                        runTower(rc);
                        break;
                }
            } catch (GameActionException e) {
                System.out.println("GameActionException");
                e.printStackTrace();

            } catch (Exception e) {
                System.out.println("Exception");
                e.printStackTrace();

            } finally {
                Clock.yield();
            }
        }

    }
    //LEAVE THIS ALONE.

    /**
     * Run a single turn for towers.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
    public static void runTower(RobotController rc) throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
        MapLocation nextLoc = rc.getLocation().add(dir);
        int robotType = rng.nextInt(3);
        if (robotType == 0 && rc.canBuildRobot(UnitType.SOLDIER, nextLoc)) {
            rc.buildRobot(UnitType.SOLDIER, nextLoc);
            System.out.println("BUILT A SOLDIER");
        } else if (robotType == 1 && rc.canBuildRobot(UnitType.MOPPER, nextLoc)) {
            rc.buildRobot(UnitType.MOPPER, nextLoc);
            System.out.println("BUILT A MOPPER");
        } else if (robotType == 2 && rc.canBuildRobot(UnitType.SPLASHER, nextLoc)) {
            rc.setIndicatorString("SPLASHER NOT IMPLEMENTED YET");
        }

        Message[] messages = rc.readMessages(-1);
        for (Message m : messages) {
            System.out.println("Tower received message: '#" + m.getSenderID() + " " + m.getBytes());
        }
    }


    //This code purposely does absolutely nothing.  Your soldiers and moppers
    //won't move at all.
    public static void runSoldier(RobotController rc) throws GameActionException {
        Direction randomDir = directions[rng.nextInt(8)];
        if (rc.canMove(randomDir)) {
            rc.move(randomDir);
        }
    }

    public static void runMopper(RobotController rc) throws GameActionException {
        Direction randomDir = directions[rng.nextInt(8)];
        if (rc.canMove(randomDir)) {
            rc.move(randomDir);
        }
    }
}
