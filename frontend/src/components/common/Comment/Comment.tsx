import * as S from '@/components/common/Comment/Comment.style';

export interface CommentProps {
	id: string;
	author: {
		name: string;
		avatar: string;
	};
	content: string;
	createAt: string;
	isAuthor: boolean;
}

const Comment = ({ id, author, content, createAt, isAuthor }: CommentProps) => {
	const onUpdateButtonClick = () => {
		// 댓글 수정 포털 열기
	};
	const onDeleteButtonClick = () => {
		// 댓글 삭제 비동기 요청 보내기
	};
	return (
		<S.Container>
			<S.CommentHeader>
				<S.CommentInfo>
					<S.UserProfile src={author.avatar} />
					<S.CommentInfoSub>
						<S.UserName>{author.name}</S.UserName>
						<S.CreateTime>{createAt}</S.CreateTime>
					</S.CommentInfoSub>
				</S.CommentInfo>
				{isAuthor && (
					<S.CommentAuthBox>
						<S.Button onClick={onUpdateButtonClick}>수정</S.Button>
						<S.Button onClick={onDeleteButtonClick}>삭제</S.Button>
					</S.CommentAuthBox>
				)}
			</S.CommentHeader>
			<S.CommentContent>{content}</S.CommentContent>
		</S.Container>
	);
};

export default Comment;
