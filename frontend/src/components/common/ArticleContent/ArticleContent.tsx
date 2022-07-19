import { useState } from 'react';

import ToastUiViewer from '@/components/common/ArticleContent/ToastUiViewer/ToastUiViewer';
import PageLayout from '@/components/layout/PageLayout/PageLayout';

import * as S from '@/components/common/ArticleContent/ArticleContent.style';
import { ArticleType } from '@/types/articleResponse';
import { Author } from '@/types/author';

export interface ArticleContentProps {
	category: string;
	article: ArticleType;
	author: Author;
}

const ArticleContent = ({ category, article, author }: ArticleContentProps) => {
	const [isHeartClick, setIsHeartClick] = useState(false);
	const onLikeButtonClick = () => {
		setIsHeartClick(!isHeartClick);
		//비동기 통신
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
								<S.Button>수정</S.Button>
								<S.Button>삭제</S.Button>
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
