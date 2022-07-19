import { useState } from 'react';

import ToastUiViewer from '@/components/common/ArticleContent/ToastUiViewer/ToastUiViewer';
import PageLayout from '@/components/layout/PageLayout/PageLayout';

import * as S from '@/components/common/ArticleContent/ArticleContent.style';
import { ArticleType } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { useMutation } from 'react-query';

import { deleteArticle } from '@/api/article';
import { useNavigate } from 'react-router-dom';

export interface ArticleContentProps {
	category: string;
	article: ArticleType;
	author: Author;
	articleId: string;
}

const ArticleContent = ({ category, article, author, articleId }: ArticleContentProps) => {
	const [isHeartClick, setIsHeartClick] = useState(false);
	const { data, isSuccess, isError, isLoading, error, mutate } = useMutation(deleteArticle);

	console.log(article.isAuthor);
	const navigate = useNavigate();

	const onLikeButtonClick = () => {
		setIsHeartClick(!isHeartClick);
		// 좋아요 비동기 통신
	};

	const postUpdateArticle = () => {
		console.log(article);
		const categoryName = category === '에러' ? 'error' : 'discussion';
		navigate(`/articles/modify/${categoryName}/${articleId}`);
	};

	const handleDeleteArticle = () => {
		if (window.confirm('게시글을 삭제하시겠습니까?')) {
			mutate(articleId);
		}
	};
	if (isLoading) {
		return <div>삭제중입니다...</div>;
	}

	if (isSuccess) {
		console.log('게시글 삭제 성공');
		navigate('/');
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
								<S.Button onClick={postUpdateArticle}>수정</S.Button>
								<S.Button onClick={handleDeleteArticle}>삭제</S.Button>
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
