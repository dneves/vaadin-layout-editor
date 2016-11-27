package com.neon.dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class DummyContentProvider implements Supplier<List< Content >> {

    public DummyContentProvider() {

    }

    @Override
    public List<Content> get() {
        List< Content > contents = new ArrayList<>( 10 );
        for (int i = 0; i < 10; i++) {
            contents.add( new Content(UUID.randomUUID().toString() ) );
        }
        return contents;
    }

}
