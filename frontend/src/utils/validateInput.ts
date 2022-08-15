export const validatedSearchInput = (target: string) => target.length >= 2;

export const validatedTitleInput = (titleInput: string) =>
	titleInput.length >= 1 && titleInput.length <= 500;
