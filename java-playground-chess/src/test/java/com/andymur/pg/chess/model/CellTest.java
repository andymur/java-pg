package com.andymur.pg.chess.model;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static com.andymur.pg.chess.model.Color.BLACK;
import static com.andymur.pg.chess.model.Color.WHITE;
import static org.hamcrest.CoreMatchers.is;

public class CellTest {
    private Cell cell;

    @Test
    public void createFirstCell() {
        cell = new Cell('A', 1);
        Assert.assertThat(cell.toString(), is("A1"));
        Assert.assertThat(cell.getColor(), is(WHITE));
    }

    @Test
    public void createSecondRowWhiteCell() {
        cell = new Cell('B', 2);
        Assert.assertThat(cell.toString(), is("B2"));
        Assert.assertThat(cell.getColor(), is(WHITE));
    }

    @Test
    public void createFirstCellWithLowerCase() {
        cell = new Cell('a', 1);
        Assert.assertThat(cell.toString(), is("A1"));
        Assert.assertThat(cell.getColor(), is(WHITE));
    }

    @Test
    public void createFirstBlackCell() {
        cell = new Cell('A', 2);
        Assert.assertThat(cell.toString(), is("A2"));
        Assert.assertThat(cell.getColor(), is(BLACK));
    }
}