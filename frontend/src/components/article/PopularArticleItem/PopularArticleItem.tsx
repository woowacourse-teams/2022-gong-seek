import { useEffect, useRef } from 'react';

import Card from '@/components/@common/Card/Card';
import * as S from '@/components/article/ArticleItem/ArticleItem.styles';
import * as PopularS from '@/components/article/PopularArticle/PopularArticle.styles';
import { theme } from '@/styles/Theme';
import { PopularArticleItemCardStyle } from '@/styles/cardStyle';
import { CommonArticleType } from '@/types/articleResponse';
import { convertGithubAvatarUrlForResize, dateTimeConverter } from '@/utils/converter';
import { css } from '@emotion/react';

const PopularArticleItem = ({
	article,
	isActive,
	onClick,
	rightSlide,
}: {
	article: CommonArticleType;
	isActive: boolean;
	onClick?: () => void;
	rightSlide?: () => void;
}) => {
	const { title, author, commentCount, views, createdAt, tag } = article;
	const timerId = useRef<number | undefined>(undefined);
	useEffect(() => {
		if (isActive && rightSlide) {
			timerId.current = window.setTimeout(rightSlide, 5000);
		}

		return () => {
			window.clearTimeout(timerId.current);
		};
	}, [isActive, rightSlide]);

	return (
		<>
			<Card {...PopularArticleItemCardStyle} isActive={isActive} onClick={onClick}>
				<div
					css={css`
						background: #ba55d3;
						opacity: 0.8;
						box-shadow: 0, 0, 0, inset 0 0 4px #5e0080;
						padding: ${theme.size.SIZE_012};
						border-top-left-radius: ${theme.size.SIZE_010};
						border-top-right-radius: ${theme.size.SIZE_010};
						margin-bottom: 1rem;
					`}
				>
					<S.ProfileBox>
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
						<div
							css={css`
								color: white;
							`}
						>
							{author.name}
						</div>
					</S.ProfileBox>
				</div>
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
							<S.CommentCount>
								<PopularS.CommentIcon />
								{commentCount}
							</S.CommentCount>
							<S.Views>
								<PopularS.ViewIcon /> {views}
							</S.Views>
						</S.ArticleInfoSubBox>
					</S.ArticleInfoBox>

					<S.HashTagListBox
						css={css`
							margin: 1rem 0;
							height: inherit;
						`}
					>
						{tag &&
							tag.length >= 1 &&
							tag.map((item) => <S.HashTagItem key={item}>#{item}</S.HashTagItem>)}
					</S.HashTagListBox>
				</div>
			</Card>
		</>
	);
};

export default PopularArticleItem;
