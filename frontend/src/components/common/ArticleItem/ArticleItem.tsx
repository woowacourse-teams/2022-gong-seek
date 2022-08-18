import { useEffect } from 'react';

import * as S from '@/components/common/ArticleItem/ArticleItem.styles';
import Loading from '@/components/common/Loading/Loading';
import useHeartClick from '@/hooks/useHeartClick';
import { queryClient } from '@/index';
import { Category } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { dateTimeConverter } from '@/utils/converter';

export interface ArticleItemProps {
	article: {
		id: number;
		title: string;
		author: Author;
		content: string;
		category: Category;
		commentCount: number;
		createdAt: string;
		tag: string[];
		isLike: boolean;
		likeCount: number;
	};
	onClick: () => void;
}

const ArticleItem = ({ article, onClick }: ArticleItemProps) => {
	const {
		deleteIsLoading,
		onLikeButtonClick,
		onUnlikeButtonClick,
		postIsLoading,
		isLike,
		likeCount,
	} = useHeartClick({
		prevIsLike: article.isLike,
		prevLikeCount: article.likeCount,
		articleId: String(article.id),
	});

	if (deleteIsLoading || postIsLoading) {
		return <Loading />;
	}

	return (
		<S.Container onClick={onClick}>
			<S.ArticleItemTitle>
				<div>{article.title}</div>
			</S.ArticleItemTitle>
			<S.ArticleInfoBox>
				<S.ArticleTimeStamp>{dateTimeConverter(article.createdAt)}</S.ArticleTimeStamp>
				<S.ArticleInfoSubBox>
					<S.CommentCount>댓글 수 {article.commentCount}</S.CommentCount>
					<S.Views>조회 수 {article.views}</S.Views>
				</S.ArticleInfoSubBox>
			</S.ArticleInfoBox>
			<S.HashTagListBox>
				{article.tag &&
					article.tag.length >= 1 &&
					article.tag.map((item) => <S.HashTagItem key={item}>#{item}</S.HashTagItem>)}
			</S.HashTagListBox>
			<S.FooterBox>
				<S.ProfileBox>
					<S.UserProfile src={article.author.avatarUrl} />
					<div>{article.author.name}</div>
				</S.ProfileBox>
				<S.RightFooterBox>
					<S.HeartBox>
						{isLike ? (
							<S.FillHeart onClick={onUnlikeButtonClick} />
						) : (
							<S.EmptyHeart onClick={onLikeButtonClick} />
						)}
						<div aria-label="좋아요 수가 표기 되는 곳입니다">{likeCount}</div>
					</S.HeartBox>
				</S.RightFooterBox>
			</S.FooterBox>
		</S.Container>
	);
};

export default ArticleItem;
