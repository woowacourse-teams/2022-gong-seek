import { useNavigate } from 'react-router-dom';

import * as S from '@/pages/MyPage/UserArticleItem/UserArticleItem.styles';
import { UserArticleItemType } from '@/types/articleResponse';
import { dateTimeConverter } from '@/utils/converter';

const UserArticleItem = ({ article }: { article: UserArticleItemType }) => {
	const navigate = useNavigate();
	const { id, title, category, commentCount, createdAt, updatedAt, views } = article;

	const 한글_카테고리 = category === 'question' ? '질문' : '토론';

	return (
		<S.Container
			onClick={() => {
				navigate(`/articles/${category}/${id}`);
			}}
		>
			<S.ArticleTitleBox>
				<S.CategoryName isQuestion={category === 'question'}>{한글_카테고리}</S.CategoryName>
				<S.ArticleTitle>{title}</S.ArticleTitle>
			</S.ArticleTitleBox>

			<S.ArticleSubInfo>
				<S.ArticleTime>
					{updatedAt.length !== 0 ? dateTimeConverter(updatedAt) : dateTimeConverter(createdAt)}
				</S.ArticleTime>

				{/*TODO: RightBox 네이밍 수정 필요 */}
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
