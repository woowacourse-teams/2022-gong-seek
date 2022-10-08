import ModalPortal from './ModalPortal';
import { useEffect } from 'react';
import { useRecoilState } from 'recoil';

import MenuSlider from '@/components/@common/MenuSlider/MenuSlider';
import * as S from '@/components/@helper/Modal/Modal.styles';
import CommentInputModal from '@/components/comment/CommentInputModal/CommentInputModal';
import { modalState } from '@/store/modalState';

export const MODAL_TYPES = {
	MENU_SLIDER: 'menu-slider',
	COMMENT_INPUT_MODAL: 'comment-modal',
} as const;

const MODAL_COMPONENTS: any = {
	[MODAL_TYPES.MENU_SLIDER]: MenuSlider,
	[MODAL_TYPES.COMMENT_INPUT_MODAL]: CommentInputModal,
};

const GlobalModal = () => {
	const [modal, setModal] = useRecoilState(modalState);
	const { modalProps, modalType, isMobileOnly } = modal ?? {};

	useEffect(() => {
		if (modalType) {
			document.body.style.overflow = 'hidden';

			return () => {
				document.body.style.overflow = 'auto';
			};
		}
	}, [modalType]);

	if (!modalType) {
		return null;
	}

	const renderComponent = () => {
		const ModalComponent = MODAL_COMPONENTS[modalType];
		return <ModalComponent {...modalProps} />;
	};

	return (
		<S.Container isMobileOnly={isMobileOnly || false}>
			<S.Dimmer onClick={() => setModal(null)} />
			<ModalPortal modalId={modalType}>{renderComponent()}</ModalPortal>
		</S.Container>
	);
};

export default GlobalModal;
