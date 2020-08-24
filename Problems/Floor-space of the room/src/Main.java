import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String type = scanner.nextLine();
        RoomCreator creator;
        switch (type) {
            case "triangle": {
                double a = scanner.nextDouble();
                double b = scanner.nextDouble();
                double c = scanner.nextDouble();
                creator = new TriangleRoomCreator(a, b, c);
            }
            break;
            case "rectangle": {
                double a = scanner.nextDouble();
                double b = scanner.nextDouble();
                creator = new RectangleRoomCreator(a, b);
            }
            break;
            case "circle":
                double r = scanner.nextDouble();
                creator = new RoundRoomCreator(r);
                break;
            default:
                creator = null;
        }
        if (creator != null) {
            Room room = creator.getRoom();
            System.out.println(room != null ? room.area() : "");
        }
    }
}

abstract class RoomCreator {
    public abstract Room getRoom();
}

class TriangleRoomCreator extends RoomCreator {
    double a;
    double b;
    double c;

    public TriangleRoomCreator(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public Room getRoom() {
        return new TriangleRoom(a, b, c);
    }
}

class RoundRoomCreator extends RoomCreator {
    double r;

    public RoundRoomCreator(double r) {
        this.r = r;
    }

    @Override
    public Room getRoom() {
        return new RoundRoom(r);
    }
}

class RectangleRoomCreator extends RoomCreator {
    double a;
    double b;

    public RectangleRoomCreator(double a, double b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public Room getRoom() {
        return new RectangleRoom(a, b);
    }
}

abstract class Room {
    abstract double area();
}

class TriangleRoom extends Room {
    double a;
    double b;
    double c;

    TriangleRoom(double a, double b, double c) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    double area() {
        double p = (a + b + c) / 2.0;

        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
}

class RoundRoom extends Room {
    double r;

    public RoundRoom(double r) {
        super();
        this.r = r;
    }

    @Override
    double area() {
        return 3.14 * r * r;
    }
}

class RectangleRoom extends Room {
    double a;
    double b;

    RectangleRoom(double a, double b) {
        super();
        this.a = a;
        this.b = b;
    }

    @Override
    double area() {
        return a * b;
    }
}
