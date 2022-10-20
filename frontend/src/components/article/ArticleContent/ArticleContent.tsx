import { useNavigate } from 'react-router-dom';

import Card from '@/components/@common/Card/Card';
import ToastUiViewer from '@/components/@common/ToastUiViewer/ToastUiViewer';
import * as S from '@/components/article/ArticleContent/ArticleContent.styles';
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
	const { handleClickFillHeart, handleClickEmptyHeart, isLike, likeCount } = useHeartClick({
		prevIsLike: article.isLike,
		prevLikeCount: article.likeCount,
		articleId,
	});
	const { handleDeleteArticle } = useDeleteArticleContent();
	const navigate = useNavigate();

	const handleClickEditButton = () => {
		const categoryName = category === '질문' ? 'question' : 'discussion';
		navigate(`/articles/modify/${categoryName}/${articleId}`);
	};

	return (
		<S.Container>
			<S.Header>
				<S.CategoryTitle tabIndex={0} aria-label={`${category}`} isQuestion={category === '질문'}>
					{category}
				</S.CategoryTitle>
				<S.UserProfile tabIndex={0}>
					<S.UserProfileImg src={author.avatarUrl} alt="작성자의 프로필 이미지입니다" />
					<div>{author.name}</div>
				</S.UserProfile>
			</S.Header>
			<Card {...ArticleContentCardStyle}>
				<S.ArticleInfo>
					<S.ArticleTitle aria-label={`글 제목, ${article.title}`} tabIndex={0}>
						{article.title}
					</S.ArticleTitle>
					<S.ArticleDetailInfo>
						<S.DetailBox
							aria-label={`글 작성시간, ${dateTimeConverter(article.createdAt)}`}
							tabIndex={0}
						>
							{dateTimeConverter(article.createdAt)}
						</S.DetailBox>
						<S.DetailBox tabIndex={0}>조회수 {article.views}</S.DetailBox>
					</S.ArticleDetailInfo>
				</S.ArticleInfo>
				<S.TextViewerBox tabIndex={0}>
					<ToastUiViewer initContent={article.content} />
				</S.TextViewerBox>
				<S.Footer>
					<S.WritingOrderBox>
						{article.isAuthor && (
							<S.ButtonWrapper>
								<S.EditButton
									onClick={handleClickEditButton}
									aria-label="글 수정하기 버튼"
									role="button"
									tabIndex={0}
								/>
								<S.DeleteButton
									onClick={() => {
										handleDeleteArticle(articleId);
									}}
									aria-label="글 삭제하기 버튼"
									role="button"
									tabIndex={0}
								/>
							</S.ButtonWrapper>
						)}
					</S.WritingOrderBox>
					<S.LikeContentBox>
						{isLike ? (
							<S.FillHeart
								onClick={handleClickFillHeart}
								aria-label="하트를 취소합니다"
								tabIndex={0}
							/>
						) : (
							<S.EmptyHeart
								onClick={handleClickEmptyHeart}
								aria-label="하트를 누릅니다"
								tabIndex={0}
							/>
						)}
						<div aria-label="좋아요 수">{likeCount}</div>
					</S.LikeContentBox>
				</S.Footer>
				<S.HashTagListBox>
					<h2 hidden>hash tag가 있다면 보여지는 곳입니다</h2>
					{article.tag &&
						article.tag.length >= 1 &&
						article.tag.map((item) => (
							<S.HashTagItem key={item} tabIndex={0}>
								<span aria-label="해시태그">#</span>
								{item}
							</S.HashTagItem>
						))}
				</S.HashTagListBox>
			</Card>
		</S.Container>
	);
};

export default ArticleContent;
