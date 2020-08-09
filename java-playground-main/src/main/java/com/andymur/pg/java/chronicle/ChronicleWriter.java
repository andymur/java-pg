package com.andymur.pg.java.chronicle;

import com.andymur.pg.java.chronicle.model.PriceUpdate;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.wire.DocumentContext;

public class ChronicleWriter {

    private ExcerptAppender appender;
    private ChronicleQueue chronicleQueue;

    public ChronicleWriter(String defaultPath) {
        chronicleQueue = ChronicleQueue.singleBuilder(defaultPath + "/trades").build();
        appender = chronicleQueue.acquireAppender();
    }

    void write(PriceUpdate priceUpdate) {
        try (DocumentContext documentContext = appender.writingDocument()) {
            documentContext.wire().write("priceUpdate").object(PriceUpdate.class, priceUpdate);
        }
    }
}
