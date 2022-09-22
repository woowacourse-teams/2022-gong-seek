export const validatedSearchInput = (target: string) => target.length >= 2 && target.length <= 200;

export const validatedTitleInput = (titleInput: string) =>
	titleInput.length >= 1 && titleInput.length <= 500;

export const validatedHashTagInput = (targetInput: string) =>
	targetInput.length >= 2 && targetInput.length <= 20;

export const validatedEditInput = (targetInput: string) => targetInput.length >= 1;

export const validatedVoteItemInputLength = (targetInput: string) =>
	targetInput.length > 0 && targetInput.length <= 300;

export const validatedDuplicatedVoteItemInput = (targetInput: string, options: string[]) => {
	const checkArray = options.concat(targetInput);
	const checkSet = new Set(checkArray);

	return checkSet.size === checkArray.length;
};

export const validatedVoteItemsQuantity = (options: string[]) =>
	options.length >= 2 && options.length <= 5;

export const validatedCommentInput = (targetInput: string) =>
	targetInput.length >= 1 && targetInput.length <= 10000;
