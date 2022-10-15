import Card from '@/components/@common/Card/Card';
import * as S from '@/components/article/ArticleItem/ArticleItem.styles';
import { theme } from '@/styles/Theme';
import { PopularArticleItemCardStyle } from '@/styles/cardStyle';
import { CommonArticleType } from '@/types/articleResponse';
import { convertGithubAvatarUrlForResize, dateTimeConverter } from '@/utils/converter';
import { css } from '@emotion/react';

const PopularArticleItem = ({
	article,
	isActive,
	onClick,
}: {
	article: CommonArticleType;
	isActive: boolean;
	onClick: () => void;
}) => {
	const { title, author, commentCount, views, createdAt, tag } = article;
	return (
		<>
			<Card {...PopularArticleItemCardStyle} isActive={isActive} onClick={onClick}>
				<S.ProfileBox
					css={css`
						background: linear-gradient(to bottom, #5e0080 7%, #3399ff 96%);
						opacity: 0.8;
						box-shadow: 0, 0, 0, inset 0 0 4px #5e0080;
						padding: ${theme.size.SIZE_012};
						border-top-left-radius: ${theme.size.SIZE_010};
						border-top-right-radius: ${theme.size.SIZE_010};
						margin-bottom: 1rem;
					`}
				>
					<S.UserProfile
						src={convertGithubAvatarUrlForResize(author.avatarUrl)}
						css={css`
							border: 2px solid transparent;
							background-image: linear-gradient(to bottom, #ffffcc 7%, #ff9900 96%),
								linear-gradient(to bottom, #ff3300 7%, #66ffcc 96%);
							background-origin: border-box;
							background-clip: content-box, border-box;
						`}
					/>
					<div>{author.name}</div>
				</S.ProfileBox>
				<div
					css={css`
						padding: ${theme.size.SIZE_014};
					`}
				>
					<S.ArticleItemTitle
						css={css`
							height: inherit;
						`}
					>
						<div>{title}</div>
					</S.ArticleItemTitle>
					<S.ArticleInfoBox>
						<S.ArticleTimeStamp>{dateTimeConverter(createdAt)}</S.ArticleTimeStamp>
						<S.ArticleInfoSubBox>
							<S.CommentCount>댓글 수 {commentCount}</S.CommentCount>
							<S.Views>조회 수 {views}</S.Views>
						</S.ArticleInfoSubBox>
					</S.ArticleInfoBox>
					<S.HashTagListBox>
						{tag &&
							tag.length >= 1 &&
							tag.map((item) => <S.HashTagItem key={item}>#{item}</S.HashTagItem>)}
					</S.HashTagListBox>
					<S.FooterBox>
						<S.RightFooterBox></S.RightFooterBox>
					</S.FooterBox>
				</div>
			</Card>
		</>
	);
};

export default PopularArticleItem;
