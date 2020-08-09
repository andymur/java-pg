package com.andymur.pg.java.chronicle;

import com.andymur.pg.java.chronicle.model.PriceUpdate;
import net.openhft.chronicle.queue.ChronicleQueue;
import net.openhft.chronicle.queue.ExcerptAppender;
import net.openhft.chronicle.queue.ExcerptTailer;
import net.openhft.chronicle.wire.DocumentContext;

public class ChronicleReader {

    private ChronicleQueue chronicleQueue;
    private ExcerptTailer tailer;

    public ChronicleReader(String defaultPath) {
        chronicleQueue = ChronicleQueue.singleBuilder(defaultPath + "/trades").build();
        tailer = chronicleQueue.createTailer();
    }

    public PriceUpdate read() {
        try (final DocumentContext documentContext = tailer.readingDocument()) {
            final PriceUpdate priceUpdate = documentContext.wire().read("priceUpdate").object(PriceUpdate.class);
            return priceUpdate;
        }
    }
}
