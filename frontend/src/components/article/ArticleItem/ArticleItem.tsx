import Card from '@/components/@common/Card/Card';
import * as S from '@/components/article/ArticleItem/ArticleItem.styles';
import useHeartClick from '@/hooks/article/useHeartClick';
import { ArticleItemCardStyle } from '@/styles/cardStyle';
import { Category } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { convertGithubAvatarUrlForResize } from '@/utils/converter';
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
		views: number;
	};
	onClick: () => void;
}

const ArticleItem = ({ article, onClick }: ArticleItemProps) => {
	const { handleClickEmptyHeart, handleClickFillHeart, isLike, likeCount } = useHeartClick({
		prevIsLike: article.isLike,
		prevLikeCount: article.likeCount,
		articleId: String(article.id),
	});

	return (
		<Card {...ArticleItemCardStyle} onClick={onClick} as="li">
			<S.ArticleItemTitle>
				<div>{article.title}</div>
			</S.ArticleItemTitle>
			<S.ArticleInfoBox>
				<S.ArticleTimeStamp>{dateTimeConverter(article.createdAt)}</S.ArticleTimeStamp>
				<S.ArticleInfoSubBox>
					<S.CommentCount>댓글 수 {article.commentCount.toLocaleString()}</S.CommentCount>
					<S.Views>조회 수 {article.views.toLocaleString()}</S.Views>
				</S.ArticleInfoSubBox>
			</S.ArticleInfoBox>
			<S.HashTagListBox>
				{article.tag &&
					article.tag.length >= 1 &&
					article.tag.map((item) => (
						<S.HashTagItem key={item}>
							<span aria-label="해시태그">#</span>
							{item}
						</S.HashTagItem>
					))}
			</S.HashTagListBox>
			<S.FooterBox>
				<S.ProfileBox>
					<S.UserProfile
						src={convertGithubAvatarUrlForResize(article.author.avatarUrl)}
						alt="프로필 이미지"
					/>
					<div>{article.author.name}</div>
				</S.ProfileBox>
				<S.RightFooterBox>
					<S.HeartBox>
						{isLike ? (
							<S.FillHeart onClick={handleClickFillHeart} aria-pressed="true" role="button" />
						) : (
							<S.EmptyHeart onClick={handleClickEmptyHeart} aria-pressed="false" role="button" />
						)}
						<div aria-label="좋아요수">{likeCount}</div>
					</S.HeartBox>
				</S.RightFooterBox>
			</S.FooterBox>
		</Card>
	);
};

export default ArticleItem;
