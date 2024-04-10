package assignment6;

public class Seat {

    private SeatType SeatType;
    private int row;
    private SeatLetter letter;

    public Seat(SeatType SeatType, int row, SeatLetter letter) {
        this.SeatType = SeatType;
        this.row = row;
        this.letter = letter;
    }

    public SeatType getSeatType() {
        return SeatType;
    }

    public void setSeatType(SeatType SeatType) {
        this.SeatType = SeatType;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public SeatLetter getLetter() {
        return letter;
    }

    public void setLetter(SeatLetter letter) {
        this.letter = letter;
    }

    @Override
    public String toString() {
        return Integer.toString(row) + letter + " (" + SeatType.toString() + ")";
    }

    static enum SeatType {
        RUMBLE(0), COMFORT(1), STANDARD(2);

        private Integer intValue;

        private SeatType(final Integer intValue) {
            this.intValue = intValue;
        }

        public Integer getIntValue() {
            return intValue;
        }
    }
    
    static enum SeatLetter {
        A(0), B(1), C(2), D(3), E(4), F(5);

        private Integer intValue;

        private SeatLetter(final Integer intValue) {
            this.intValue = intValue;
        }

        public Integer getIntValue() {
            return intValue;
        }
    }

}
