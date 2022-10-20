import { useNavigate } from 'react-router-dom';

import * as S from '@/components/user/UserCommentItem/UserCommentItem.styles';
import { UserComment } from '@/types/commentResponse';
import { dateTimeConverter } from '@/utils/converter';

const UserCommentItem = ({ comment }: { comment: UserComment }) => {
	const { id, content, createdAt, updatedAt, articleId, category, articleTitle } = comment;
	const navigate = useNavigate();
	const 한글_카테고리 = category === 'question' ? '질문' : '토론';

	const handleClickUserCommentItem = () => {
		navigate(`/articles/${category}/${articleId}`);
	};

	return (
		<S.Container onClick={handleClickUserCommentItem}>
			<S.ArticleBox>
				<S.ArticleCategory isQuestion={category === 'question'}>{한글_카테고리}</S.ArticleCategory>
				<S.ArticleTitle>{articleTitle}</S.ArticleTitle>
			</S.ArticleBox>

			<S.ContentBox>
				<S.ContentLabel>댓글: </S.ContentLabel>
				{content}
			</S.ContentBox>
			<S.CommentTime>
				{updatedAt.length !== 0 ? dateTimeConverter(updatedAt) : dateTimeConverter(createdAt)}
			</S.CommentTime>
		</S.Container>
	);
};

export default UserCommentItem;
