package com.ryan.fransen;

public class Constants {
    static final String b_bedmas_pattern =  "\\((-?\\d+(?:\\.\\d+)?(?:[\\*\\/\\+\\-\\^r]-?\\d+(?:\\.\\d+)?)+)\\)";   // (2*3)
    static final String e_bedmas_pattern =  "(-?\\d+(?:\\.\\d+)?[\\^r]-?\\d+(?:\\.\\d+)?)";                          // 2^3, 2r4
    static final String m_bedmas_pattern =  "(-?\\d+(?:\\.\\d+)?[\\*]-?\\d+(?:\\.\\d+)?)";                          // 2*3
    static final String a_bedmas_pattern =  "(-?\\d+(?:\\.\\d+)?[\\+]-?\\d+(?:\\.\\d+)?)";                          // 2+3
    static final String s_bedmas_pattern =  "(-?\\d+(?:\\.\\d+)?[\\-]-?\\d+(?:\\.\\d+)?)";                          // 3-1
    static final String d_bedmas_pattern =  "(-?\\d+(?:\\.\\d+)?[\\/]-?\\d+(?:\\.\\d+)?)";                          // 9/3
    static final String atomic_pattern =    "(-?\\d+(?:\\.\\d+)?)([\\^r\\+\\-\\*\\/])(-?\\d+(?:\\.\\d+)?)";          // 2[^*/+-]3

    static String[] edmas_patterns = new String[] {
            e_bedmas_pattern,
            d_bedmas_pattern,
            m_bedmas_pattern,
            a_bedmas_pattern,
            s_bedmas_pattern
    };

    static String msg_operator_error = "Invalid operator: %s";
    static String msg_invalid_expression = "Invalid expression: %s";
}
