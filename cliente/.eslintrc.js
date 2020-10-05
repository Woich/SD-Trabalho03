module.exports = {
    env: {
        browser: true,
        es2021: true
    },
    extends: ['standard'],
    parserOptions: {
        ecmaVersion: 12,
        sourceType: 'module'
    },
    rules: {
        // we only want single quotes
        quotes: ['error', 'single'],
        // we want to force semicolons
        semi: ['error', 'always'],
        // we use 2 spaces to indent our code
        indent: ['error', 4, { SwitchCase: 1 }],
        // we want to avoid useless spaces
        'no-multi-spaces': ['error']
    }
};
