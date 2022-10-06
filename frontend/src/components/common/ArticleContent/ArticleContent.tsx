import { useNavigate } from 'react-router-dom';

import * as S from '@/components/common/ArticleContent/ArticleContent.styles';
import Card from '@/components/common/Card/Card';
import ToastUiViewer from '@/components/common/ToastUiViewer/ToastUiViewer';
import useDeleteArticleContent from '@/hooks/article/useDeleteArticleContent';
import useHeartClick from '@/hooks/article/useHeartClick';
import { ArticleContentCardStyle } from '@/styles/cardStyle';
import { ArticleType } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { dateTimeConverter } from '@/utils/converter';

export interface ArticleContentProps {
	category: string;
	article: ArticleType;
	author: Author;
	articleId: string;
}

const ArticleContent = ({ category, article, author, articleId }: ArticleContentProps) => {
	const { onLikeButtonClick, onUnlikeButtonClick, isLike, likeCount } = useHeartClick({
		prevIsLike: article.isLike,
		prevLikeCount: article.likeCount,
		articleId,
	});
	const { handleDeleteArticle } = useDeleteArticleContent();
	const navigate = useNavigate();

	const navigateUpdateArticle = () => {
		const categoryName = category === '질문' ? 'question' : 'discussion';
		navigate(`/articles/modify/${categoryName}/${articleId}`);
	};

	return (
		<S.Container>
			<S.Header>
				<S.CategoryTitle category={category}>{category}</S.CategoryTitle>
				<S.UserProfile>
					<S.UserProfileImg src={author.avatarUrl} />
					<div>{author.name}</div>
				</S.UserProfile>
			</S.Header>
			<Card {...ArticleContentCardStyle}>
				<S.ArticleInfo>
					<S.ArticleTitle>{article.title}</S.ArticleTitle>
					<S.ArticleDetailInfo>
						<S.DetailBox>{dateTimeConverter(article.createdAt)}</S.DetailBox>
						<S.DetailBox>조회수 {article.views}</S.DetailBox>
					</S.ArticleDetailInfo>
				</S.ArticleInfo>
				<S.TextViewerBox>
					<ToastUiViewer initContent={article.content} />
				</S.TextViewerBox>
				<S.Footer>
					<S.WritingOrderBox>
						{article.isAuthor && (
							<S.ButtonWrapper>
								<S.Button onClick={navigateUpdateArticle}>수정</S.Button>
								<S.Button
									onClick={() => {
										handleDeleteArticle(articleId);
									}}
								>
									삭제
								</S.Button>
							</S.ButtonWrapper>
						)}
					</S.WritingOrderBox>
					<S.LikeContentBox>
						{isLike ? (
							<S.FillHeart onClick={onUnlikeButtonClick} />
						) : (
							<S.EmptyHeart onClick={onLikeButtonClick} />
						)}
						<div>{likeCount}</div>
					</S.LikeContentBox>
				</S.Footer>
				<S.HashTagListBox>
					<h2 hidden>hash tag가 있다면 보여지는 곳입니다</h2>
					{article.tag &&
						article.tag.length >= 1 &&
						article.tag.map((item) => <S.HashTagItem key={item}>#{item}</S.HashTagItem>)}
				</S.HashTagListBox>
			</Card>
		</S.Container>
	);
};

export default ArticleContent;
