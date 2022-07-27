import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import ToastUiViewer from '@/components/common/ArticleContent/ToastUiViewer/ToastUiViewer';
import Loading from '@/components/common/Loading/Loading';
import PageLayout from '@/components/layout/PageLayout/PageLayout';
import useDeleteArticleContent from '@/components/common/ArticleContent/hooks/useDeleteArticleContent';
import { ArticleType } from '@/types/articleResponse';
import { Author } from '@/types/author';

import * as S from '@/components/common/ArticleContent/ArticleContent.styles';
export interface ArticleContentProps {
	category: string;
	article: ArticleType;
	author: Author;
	articleId: string;
}

const ArticleContent = ({ category, article, author, articleId }: ArticleContentProps) => {
	const [isHeartClick, setIsHeartClick] = useState(false);
	const { isLoading, handleDeleteArticle } = useDeleteArticleContent();
	const navigate = useNavigate();

	const onLikeButtonClick = () => {
		setIsHeartClick(!isHeartClick);
		// 좋아요 비동기 통신
	};

	const navigateUpdateArticle = () => {
		const categoryName = category === '에러' ? 'question' : 'discussion';
		navigate(`/articles/modify/${categoryName}/${articleId}`);
	};

	if (isLoading) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.Header>
				<S.CategoryTitle category={category}>{category}</S.CategoryTitle>
				<S.UserProfile>
					<S.UserProfileImg src={author.avatarUrl} />
					<div>{author.name}</div>
				</S.UserProfile>
			</S.Header>
			<PageLayout flexDirection="column" width="90%" padding="0.7rem">
				<S.ArticleInfo>
					<S.ArticleTitle>{article.title}</S.ArticleTitle>
					<S.ArticleDetailInfo>
						<S.DetailBox>{article.createdAt}</S.DetailBox>
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
						{isHeartClick ? (
							<S.FillHeart onClick={onLikeButtonClick} />
						) : (
							<S.EmptyHeart onClick={onLikeButtonClick} />
						)}
						<div>{article.likeCount}</div>
					</S.LikeContentBox>
				</S.Footer>
			</PageLayout>
		</S.Container>
	);
};

export default ArticleContent;
