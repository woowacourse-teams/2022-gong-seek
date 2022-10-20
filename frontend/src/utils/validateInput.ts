import { ModalStateType } from '@/store/modalState';

export const validatedSearchInput = (target: string) => target.length >= 2 && target.length <= 200;

export const validatedTitleInput = (titleInput: string) =>
	titleInput.length >= 1 && titleInput.length <= 500;

export const validatedHashTagInput = (targetInput: string) =>
	targetInput.length >= 2 && targetInput.length <= 20;

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

export const validatedUserNameInput = (targetInput: string) =>
	targetInput.length >= 1 && targetInput.length < 255;

export const hasModal = (
	modalType: ModalStateType['modalType'] | undefined,
): modalType is ModalStateType['modalType'] => typeof modalType !== 'undefined';
