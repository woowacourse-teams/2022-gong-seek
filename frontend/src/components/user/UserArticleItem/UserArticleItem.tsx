import { useNavigate } from 'react-router-dom';

import * as S from '@/components/user/UserArticleItem/UserArticleItem.styles';
import { UserArticleItemType } from '@/types/articleResponse';
import { categoryNameConverter, dateTimeConverter } from '@/utils/converter';

const UserArticleItem = ({ article }: { article: UserArticleItemType }) => {
	const navigate = useNavigate();
	const { id, title, category, commentCount, createdAt, updatedAt, views } = article;

	const handleClickUserArticleItem = () => {
		navigate(`/articles/${category}/${id}`);
	};

	return (
		<S.Container onClick={handleClickUserArticleItem}>
			<S.ArticleTitleBox>
				<S.CategoryName isQuestion={category === 'question'}>
					{categoryNameConverter(category)}
				</S.CategoryName>
				<S.ArticleTitle>{title}</S.ArticleTitle>
			</S.ArticleTitleBox>

			<S.ArticleSubInfo>
				<S.ArticleTime>
					{updatedAt.length !== 0 ? dateTimeConverter(updatedAt) : dateTimeConverter(createdAt)}
				</S.ArticleTime>
				<S.ArticleRightBox>
					<S.CommentBox>
						<S.CommentIcon
							aria-label="게시글의 댓글 갯수가 표기됩니다"
							aria-labelledby="comment-count"
						/>
						<S.CommentCount id="comment-count">{commentCount}</S.CommentCount>
					</S.CommentBox>
					<S.Views>조회수 {views}</S.Views>
				</S.ArticleRightBox>
			</S.ArticleSubInfo>
		</S.Container>
	);
};

export default UserArticleItem;
