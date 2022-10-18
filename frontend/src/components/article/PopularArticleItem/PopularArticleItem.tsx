import { useEffect, useRef } from 'react';

import Card from '@/components/@common/Card/Card';
import * as S from '@/components/article/ArticleItem/ArticleItem.styles';
import * as PopularS from '@/components/article/PopularArticleItem/PopularArticleItem.styles';
import { CAROUSEL_AUTO_PLAY_TIME } from '@/constants/index';
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
	const { title, author, commentCount, views, createdAt, tag, category, likeCount } = article;
	const timerId = useRef<number | undefined>(undefined);
	const 한글_카테고리 = category === 'question' ? '질문' : '토론';

	useEffect(() => {
		if (isActive && rightSlide) {
			timerId.current = window.setTimeout(rightSlide, CAROUSEL_AUTO_PLAY_TIME);
		}

		return () => {
			window.clearTimeout(timerId.current);
		};
	}, [isActive, rightSlide]);

	return (
		<>
			<Card {...PopularArticleItemCardStyle} isActive={isActive} onClick={onClick} role="tab">
				<div
					css={css`
						background: ${category === 'question' ? theme.colors.RED_500 : theme.colors.BLUE_500};
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
							alt="프로필 이미지"
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
						<PopularS.CategoryBox categoryType={category}>{한글_카테고리}</PopularS.CategoryBox>
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
							<PopularS.IconContainer>
								<PopularS.CommentIcon aria-label="댓글수" />
								{commentCount.toLocaleString()}
							</PopularS.IconContainer>
							<PopularS.IconContainer>
								<PopularS.ViewIcon aria-label="조회수" />
								{views.toLocaleString()}
							</PopularS.IconContainer>
							<PopularS.IconContainer>
								<PopularS.HeartIcon aria-label="좋아요수" />
								{likeCount.toLocaleString()}
							</PopularS.IconContainer>
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
							tag.map((item) => (
								<S.HashTagItem key={item}>
									<span aria-label="해시태그">#</span>
									{item}
								</S.HashTagItem>
							))}
					</S.HashTagListBox>
				</div>
			</Card>
		</>
	);
};

export default PopularArticleItem;
