package org.apache.commons.collections4.trie;

import junit.framework.TestCase;
import org.apache.commons.collections4.Trie;

/**
 * Regression test for COLLECTIONS-495:
 * UnmodifiableTrie.unmodifiableTrie should return the same instance if the trie is already unmodifiable.
 */
public class UnmodifiableTrieRegressionTest extends TestCase {

    public void testUnmodifiableTrieDecorateTwice() {
        final Trie<String, String> trie = new PatriciaTrie<String>();
        final Trie<String, String> unmodifiableTrie = UnmodifiableTrie.unmodifiableTrie(trie);
        assertSame("UnmodifiableTrie shall not be decorated again",
                unmodifiableTrie, UnmodifiableTrie.unmodifiableTrie(unmodifiableTrie));
    }
}