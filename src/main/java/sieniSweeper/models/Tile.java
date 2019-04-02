package sieniSweeper.models;

public class Tile {
    private int content;
    private Boolean visible;

    public Tile() {
        /**
         * Contains mine when content equals 10
         * Mine is nearby when mine is greater than 0
         * Tile is safe when content equals 0
         */
        this.content = 0;
        this.visible = false;
    }


    public void setVisible() {
        this.visible = true;
    }

    public int getContent() {
        return this.content;
    }

    public void setMine() {
        this.content = 10;
    }

    public int getContentIfVisible() {
        /**
         * Returns -1 in case tile is not visible
         */
        if (this.visible) {
            return this.content;
        }

        return  -1;
    }

    public void incrementContent() {
        /**
         * Intended to set numbers around mines
         */
        if (this.content != 10) {
            this.content += 1;
        }
    }

    @Override
    public String toString() {
        return getContentIfVisible() + "";
    }

    public boolean hasMineSweeped() {
        return this.content == 10 && this.visible;
    }

    public boolean hasMine() {
        return this.content == 10;
    }

    public boolean isVisible() {
        return this.visible;
    }
}
