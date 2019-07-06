package com.andymur.pg.java.genestack;

/**
 * Created by andymur on 6/18/17.
 */
public class EncircularRunner {

    public static void main(String[] args) {
        String[] res = doesCircleExist(new String[] {"GRGLGR"});

        for (String answer: res) {
            System.out.println(answer);
        }
    }

    static String[] doesCircleExist(String[] commands) {
        String[] result = new String[commands.length];

        for (int i = 0; i < commands.length; i++) {
            result[i] = doesCircleExist(commands[i]);
        }

        return result;
    }

    static String doesCircleExist(String command) {
        Coordinate zero = new Coordinate(0, 0);
        Coordinate position = new Coordinate(0, 0);
        Direction direction = Direction.NORTH;

        for (int i = 0; i < 4; i++) {

            for (Character step : command.toCharArray()) {
                if (isRotation(step)) {
                    direction = rotate(direction, step);
                } else {
                    position = oneStepFurther(position, direction);
                }
            }

            if (position.equals(zero)) {
                return "YES";
            }

        }

        return position.equals(zero) ? "YES" : "NO";
    }

    static boolean isRotation(Character command) {
        return command == 'R' || command == 'L';
    }

    static Direction rotate(Direction currentDirection, Character rotation) {
        return currentDirection.rotate(rotation);
    }

    static Coordinate oneStepFurther(Coordinate currentPosition, Direction direction) {
        // NORTH, Y + 1
        // SOUTH Y - 1
        // WEST X - 1
        // EAST X + 1
        return currentPosition.stepFurther(direction);
    }

    static enum Direction {
        NORTH, SOUTH, WEST, EAST;

        Direction rotate(Character rotation) {
            if (this == WEST) {
                return rotation == 'R' ? NORTH : SOUTH;
            } else if (this == EAST) {
                return rotation == 'R' ? SOUTH : NORTH;
            } else if (this == NORTH) {
                return rotation == 'R' ? EAST : WEST;
            } else if (this == SOUTH) {
                return rotation == 'R' ? WEST : EAST;
            }

            throw new IllegalArgumentException("Wrong rotation");
        }
    }

    static class Coordinate {
        public final int x;
        public final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coordinate stepFurther(Direction direction) {
            switch (direction) {
                case NORTH:
                    return new Coordinate(x, y + 1);
                case SOUTH:
                    return new Coordinate(x, y - 1);
                case WEST:
                    return new Coordinate(x - 1, y);
                case EAST:
                    return  new Coordinate(x + 1, y);
                default:
                    throw  new IllegalArgumentException("Wrong direction");
            }
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Coordinate{");
            sb.append("x=").append(x);
            sb.append(", y=").append(y);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Coordinate that = (Coordinate) o;

            if (x != that.x) return false;
            if (y != that.y) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
