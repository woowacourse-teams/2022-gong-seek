import reactDom from 'react-dom';
import * as S from '@/components/common/CommentInputModal/CommentInputModal.styles';
import { useState } from 'react';

export interface CommentInputModalProps {
	closeModal: () => void;
}

const CommentInputModal = ({ closeModal }: CommentInputModalProps) => {
	const commentModal = document.getElementById('comment-portal');
	const [comment, setComment] = useState('');

	return reactDom.createPortal(
		<S.CommentContainer>
			<S.CommentTitle>댓글 작성하기</S.CommentTitle>
			<S.CommentContent
				aria-label="댓글을 입력해주세요"
				value={comment}
				onChange={(e) => setComment(e.target.value)}
			></S.CommentContent>
			<S.CommentPostButton
				onClick={() => {
					console.log(comment);
				}}
			>
				등록하기
			</S.CommentPostButton>
		</S.CommentContainer>,
		commentModal as HTMLElement,
	);
};
export default CommentInputModal;
