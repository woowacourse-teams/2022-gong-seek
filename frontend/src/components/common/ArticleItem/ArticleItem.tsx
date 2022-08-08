import * as S from '@/components/common/ArticleItem/ArticleItem.styles';
import Loading from '@/components/common/Loading/Loading';
import useHeartClick from '@/hooks/useHeartClick';
import { Category, CommonArticleType } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { dateTimeConverter, exculdeSpecialWordConverter } from '@/utils/converter';

interface ArticleItemProps {
	article: {
		id: number;
		title: string;
		author: Author;
		content: string;
		category: Category;
		commentCount: number;
		createdAt: string;
		isLike: boolean;
		like: number;
	};
	onClick: () => void;
}

const ArticleItem = ({ article, onClick }: ArticleItemProps) => {
	const { deleteIsLoading, isHeartClick, onLikeButtonClick, onUnlikeButtonClick, postIsLoading } =
		useHeartClick(article.isLike, String(article.id));

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
				<S.Views>댓글 수 {article.commentCount}</S.Views>
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
							<S.FillHeart onClick={onUnlikeButtonClick} />
						) : (
							<S.EmptyHeart onClick={onLikeButtonClick} />
						)}
						<div>{article.like}</div>
					</S.HeartBox>
				</S.RightFooterBox>
			</S.FooterBox>
		</S.Container>
	);
};

export default ArticleItem;
