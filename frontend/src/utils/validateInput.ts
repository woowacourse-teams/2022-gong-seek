export const validatedSearchInput = (target: string) => target.length >= 2;

export const validatedTitleInput = (titleInput: string) =>
	titleInput.length >= 1 && titleInput.length <= 500;

export const validatedHashTagInput = (target: string) => target.length >= 2 && target.length <= 20;
