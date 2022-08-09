import { useState } from 'react';

import * as S from '@/components/common/ArticleItem/ArticleItem.styles';
import { Category } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { dateTimeConverter, exculdeSpecialWordConverter } from '@/utils/converter';

export interface ArticleItemProps {
	article: {
		id: number;
		title: string;
		author: Author;
		content: string;
		category: Category;
		commentCount: number;
		createdAt: string;
		hashTag: string[];
		views: number;
	};
	onClick: () => void;
}

const ArticleItem = ({ article, onClick }: ArticleItemProps) => {
	const [isHeartClick, setIsHeartClick] = useState(false);

	const onLikeButtonClick = () => {
		setIsHeartClick(!isHeartClick);
	};

	return (
		<S.Container onClick={onClick}>
			<S.ArticleItemTitle>
				<div>{article.title}</div>
			</S.ArticleItemTitle>
			<S.ArticleInfoBox>
				<S.ArticleTimeStamp>{dateTimeConverter(article.createdAt)}</S.ArticleTimeStamp>
				<S.CommentCount>댓글 수 {article.commentCount}</S.CommentCount>
				<S.Views>조회 수 {article.views}</S.Views>
			</S.ArticleInfoBox>
			<S.Content>{exculdeSpecialWordConverter(article.content)}</S.Content>
			<S.FooterBox>
				<S.ProfileBox>
					<S.UserProfile src={article.author.avatarUrl} />
					<div>{article.author.name}</div>
				</S.ProfileBox>
				<S.RightFooterBox>
					<S.HeartBox>
						{isHeartClick ? (
							<S.FillHeart onClick={onLikeButtonClick} />
						) : (
							<S.EmptyHeart onClick={onLikeButtonClick} />
						)}
						<div>10</div>
					</S.HeartBox>
				</S.RightFooterBox>
			</S.FooterBox>
			<S.HashTagListBox>
				{article.hashTag.length >= 1 &&
					article.hashTag.map((item) => <S.HashTagItem key={item}>#{item}</S.HashTagItem>)}
			</S.HashTagListBox>
		</S.Container>
	);
};

export default ArticleItem;
