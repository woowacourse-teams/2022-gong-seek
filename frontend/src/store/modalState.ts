import { atom } from 'recoil';

import { MODAL_TYPES } from '@/components/@helper/Modal/GlobalModal';
import { CommentInputModalProps } from '@/components/comment/CommentInputModal/CommentInputModal';

interface MenuSliderType {
	modalType: typeof MODAL_TYPES.MENU_SLIDER;
	modalProps: Record<string, never>;
	isMobileOnly: true;
}

interface CommentInputModalType {
	modalType: typeof MODAL_TYPES.COMMENT_INPUT_MODAL;
	modalProps: CommentInputModalProps;
	isMobileOnly: false;
}

export type ModalStateType = MenuSliderType | CommentInputModalType;

export const modalState = atom<ModalStateType | null>({
	key: 'modalState',
	default: null,
});
