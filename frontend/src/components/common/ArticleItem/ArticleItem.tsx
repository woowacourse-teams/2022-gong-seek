import { useState } from 'react';
import * as S from '@/components/common/ArticleItem/ArticleItem.styles';
import { Category, CommonArticleType } from '@/types/articleResponse';
import { Author } from '@/types/author';

interface ArticleItemProps {
	article: {
		id: number;
		title: string;
		author: Author;
		content: string;
		category: Category;
		commentCount: number;
		createdAt: string;
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
				<S.ArticleTimeStamp>{article.createdAt}</S.ArticleTimeStamp>
				<S.Views>댓글 수 {article.commentCount}</S.Views>
			</S.ArticleInfoBox>
			<S.Content>{article.content}</S.Content>
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
		</S.Container>
	);
};

export default ArticleItem;
