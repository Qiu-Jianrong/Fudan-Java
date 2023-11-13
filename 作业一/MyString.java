import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class MyString {
    private final char [] value;
    MyString(char[] v){
        // Attention that clone is needed.
        value = Arrays.copyOf(v, v.length);
    }

    public int length(){
        return value.length;
    }

    /**
     *
     * @param v: the pattern string.
     * @param idx: the idx of the first matched character.
     * @return Whether v[1: -1] equals to value[idx + 1: idx + v.length).
     */
    private boolean match_rest(char[] v, int idx){
        // If value do not hava enough characters left to match
        // Actually, it is a redundant check, but it stays for robustness.
        int v_len = v.length;
        int value_len = value.length;
        if(v_len > value_len - idx)
            return false;
        // Start from the index 1. If v has only one character, directly true.
        idx += 1;
        for (int v_idx = 1; v_idx < v_len; ++v_idx, ++idx){
            if (v[v_idx] != value[idx])
                return false;
        }
        return true;
    }

    /**
     * Search for substring, but start from the given index.
     * @param target: the wanted substring
     * @param begin: the beginning of search
     * @return The beginning of substring(if exists), -1 if not a substring.
     */
    private int indexOf(char[] target, int begin){
        int target_len = target.length;
        int value_len = value.length;
        // Empty string is always a substring.
        if(target_len == 0){
            return 0;
        }
        // If v is longer, return -1.
        if (target_len > value_len - begin)
            return -1;

        for (int idx = begin; idx <= value_len - target_len; ++idx){
            // Once match, return immediately.
            if(value[idx] == target[0] && match_rest(target, idx)){
                return idx;
            }
        }
        return -1;
    }
    public int indexOf(char[] v){
        return indexOf(v, 0);
    }

    /**
     * Concat v to this.value
     * @param v: the string to be appended
     * @return a new string
     */
    public MyString concat(char[] v) {
        int appendLen = v.length;
        // If v is an empty string, return original string.
        if (appendLen == 0) {
            return this;
        }

        // Copy first half, then the appended one.
        int len = value.length;
        char[] res = Arrays.copyOf(value, len + appendLen);
        System.arraycopy(v, 0, res, len, appendLen);
        return new MyString(res);
    }

    /**
     * Helper Function. Fill in 'receiver' from 'begin' with 'giver'.
     * We do not use 'concat' interface in 'replace' for three reasons:
     * 1. 'concat' will allocate excess space, which we have done already.
     * 2. 'concat' will create temporary object, which is bad for performance.
     * 3. 'concat' cannot append a single character, which is why we allocate memory first.
     * @param receiver: content receiver
     * @param begin: where to begin
     * @param giver: content giver
     * @return The end of filled sequence
     */
    private int fillin(char[] receiver, int begin, char[] giver){
        int give_len = giver.length;
//        if (give_len == 0)
//            return begin;
        for (int idx = 0; idx < give_len; ++idx, ++begin){
            receiver[begin] = giver[idx];
        }
        return begin;
    }

    /**
     * Replace all targetStr with replaceStr, iff targetStr is a substring of this.value
     * @param targetStr: string to be replaced
     * @param replaceStr: string to replace
     * @return a brand-new string
     */
    public MyString replace(char[] targetStr, char[] replaceStr){
        int thisLen = this.length();
        int trgtLen = targetStr.length;
        int replLen = replaceStr.length;

        // If targetStr is not an empty string.
        if (trgtLen > 0) {
            // if targetStr is not a substring, return original string.
            int begin = this.indexOf(targetStr);
            if (begin == -1){
                return this;
            }

            // Use a queue to store the replacing spot.
            Queue<Integer> indexs = new LinkedList<>();
            indexs.add(begin);
            while (true){
                // By testing, we know that String do not overlap match
                begin = this.indexOf(targetStr, begin + trgtLen);
                if (begin == -1)
                    break;
                indexs.add(begin);
            }

            // Allocate memory for the result string 'res'.
            int resultLen = thisLen + (replLen - trgtLen) * indexs.size();
            char[] res = new char[resultLen];

            // In turn, fill the 'res' with original string and targetStr.
            int res_idx = 0;
            int old_index = -trgtLen;
            while (!indexs.isEmpty()){
                int index = indexs.poll();
                res_idx = fillin(res, res_idx, Arrays.copyOfRange(this.value, old_index + trgtLen, index));
                res_idx = fillin(res, res_idx, replaceStr);
                old_index = index;
            }
            fillin(res, res_idx, Arrays.copyOfRange(this.value, old_index + trgtLen, thisLen));

            return new MyString(res);
        } else {
            // trgtLen == 0, now embed replaceStr into every gap.

            // The following code block is from source code, the aim is to calculate the total length of result.
            int resultLen;
            try {
                resultLen = Math.addExact(thisLen, Math.multiplyExact(
                        Math.addExact(thisLen, 1), replLen));
            } catch (ArithmeticException ignored) {
                throw new OutOfMemoryError("Required length exceeds implementation limit");
            }
            char[] res = new char[resultLen];

            // 'begin' is the index of the result string.
            int begin = 0;
            begin = fillin(res, begin, replaceStr);
            for (int i = 0; i < thisLen; ++i) {
                res[begin] = this.value[i];
                begin += 1;
                begin = fillin(res, begin, replaceStr);
            }

            // Assert 'begin' move to the end.
            assert begin == resultLen;
            return new MyString(res);
        }
    }
    public char[] getValue(){
        return this.value.clone();
    }
}
