export const validatedSearchInput = (target: string) => target.length >= 2;

export const validateHashTagInput = (target: string) => target.length >= 2 && target.length <= 20;
