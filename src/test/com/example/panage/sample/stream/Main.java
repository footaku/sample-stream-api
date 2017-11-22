package com.example.panage.sample.stream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author fu-taku
 */
@SuppressWarnings("NonAsciiCharacters")
@RunWith(PowerMockRunner.class)
public class Main {

    @Test
    public void listからlistへ() {
        // given
        List<String> source = new ArrayList<>();
        source.add("1");
        source.add("2");
        source.add("3");
        List<String> target;

        // when
        //noinspection SimplifyStreamApiCallChains
        target = source.stream().collect(Collectors.toList());

        // then
        assertEquals(source, target);

        // when
        target = source.stream().filter(s -> s.equals("1")).collect(Collectors.toList());

        // then
        assertNotEquals(source, target);
        assertEquals(target.size(), 1);
        assertEquals(target.get(0), "1");
    }

    @Test
    public void mapからlistへ() {
        // given
        @SuppressWarnings("unchecked")
        Map<String, String> map = new HashMap() {{
            put("1", "one");
            put("2", "two");
            put("3", "three");
        }};

        // when
        List<String> actual = map.entrySet().stream()
                .map(Map.Entry::getValue)
                .collect(ArrayList::new, List::add, List::addAll);

        // then
        assertEquals(3, actual.size());
        assertEquals("one", actual.get(0));
        assertEquals("two", actual.get(1));
        assertEquals("three", actual.get(2));
    }

    @Test
    public void listからmapへ() {
        // given
        List<Data> list = new ArrayList<>();
        list.add(new Data("1", "2", "3"));
        list.add(new Data("2", "3", "4"));
        list.add(new Data("3", "4", "5"));

        // when
        Map<String, Data> actual = list.stream().collect(Collectors.toMap(Data::getHoge, o -> o));

        // then
        assertEquals(3, actual.size());
        assertEquals(new Data("1", "2", "3"), actual.get("1"));
        assertEquals(new Data("2", "3", "4"), actual.get("2"));
        assertEquals(new Data("3", "4", "5"), actual.get("3"));

        // when
        actual = list.stream()
                .filter(data -> Integer.parseInt(data.getPiyo()) % 2 == 0)
                .collect(Collectors.toMap(Data::getHoge, o -> o));

        // then
        assertEquals(new Data("2", "3", "4"), actual.get("2"));
    }

    private static class Data {
        private String hoge;
        private String fuga;
        private String piyo;

        public Data() {
        }

        public Data(String hoge, String fuga, String piyo) {
            this.hoge = hoge;
            this.fuga = fuga;
            this.piyo = piyo;
        }

        /**
         * @return hoge
         */
        public String getHoge() {
            return hoge;
        }

        /**
         * @param hoge
         */
        public void setHoge(String hoge) {
            this.hoge = hoge;
        }

        /**
         * @return fuga
         */
        public String getFuga() {
            return fuga;
        }

        /**
         * @param fuga
         */
        public void setFuga(String fuga) {
            this.fuga = fuga;
        }

        /**
         * @return piyo
         */
        public String getPiyo() {
            return piyo;
        }

        /**
         * @param piyo
         */
        public void setPiyo(String piyo) {
            this.piyo = piyo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Data data = (Data) o;

            if (hoge != null ? !hoge.equals(data.hoge) : data.hoge != null) return false;
            if (fuga != null ? !fuga.equals(data.fuga) : data.fuga != null) return false;
            return piyo != null ? piyo.equals(data.piyo) : data.piyo == null;
        }

        @Override
        public int hashCode() {
            int result = hoge != null ? hoge.hashCode() : 0;
            result = 31 * result + (fuga != null ? fuga.hashCode() : 0);
            result = 31 * result + (piyo != null ? piyo.hashCode() : 0);
            return result;
        }
    }


}
