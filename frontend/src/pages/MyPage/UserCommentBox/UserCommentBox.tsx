import * as S from '@/pages/MyPage/UserCommentBox/UserCommentBox.styles';
import { UserComment } from '@/types/commentResponse';

const UserCommentBox = ({ comment }: { comment: UserComment }) => {
	const { id, content, createdAt, updatedAt } = comment;

	return (
		<S.Container>
			<S.ContentBox>{content}</S.ContentBox>
			<S.CommentTime>{updatedAt.length !== 0 ? updatedAt : createdAt}</S.CommentTime>
		</S.Container>
	);
};

export default UserCommentBox;
