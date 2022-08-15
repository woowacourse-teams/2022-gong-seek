export const validatedSearchInput = (target: string) => target.length >= 2;

export const validatedTitleInput = (titleInput: string) =>
	titleInput.length >= 1 && titleInput.length <= 500;

export const validatedHashTagInput = (targetInput: string) =>
	targetInput.length >= 2 && targetInput.length <= 20;
