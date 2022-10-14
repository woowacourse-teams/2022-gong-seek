import { AiFillEdit, AiOutlineMenu } from 'react-icons/ai';
import { Link } from 'react-router-dom';

import { css } from '@emotion/react';
import styled from '@emotion/styled';

export const Section = styled.section`
	display: flex;
	position: fixed;

	bottom: 0;

	justify-content: space-between;
	align-items: center;

	width: 100vw;

	${({ theme }) => css`
		height: ${theme.size.SIZE_056};

		border-radius: ${theme.size.SIZE_010} ${theme.size.SIZE_010} 0 0;

		box-shadow: 0 -${theme.size.SIZE_004} 15px ${theme.boxShadows.secondary};
		background-color: ${theme.colors.WHITE};

		@media (min-width: ${theme.breakpoints.DESKTOP_SMALL}) {
			display: none;
			visibility: hidden;
		}
	`}
`;

export const PostingLink = styled(AiFillEdit)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_026};

		color: ${theme.colors.BLACK_500};

		margin: ${theme.size.SIZE_016} 0 ${theme.size.SIZE_016} ${theme.size.SIZE_016};

		cursor: pointer;

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
		}
	`}
`;

export const MyPageLink = styled(Link)``;

export const MenuLink = styled(AiOutlineMenu)`
	${({ theme }) => css`
		font-size: ${theme.size.SIZE_024};

		color: ${theme.colors.BLACK_500};

		margin: ${theme.size.SIZE_016} ${theme.size.SIZE_016} ${theme.size.SIZE_016}0;

		cursor: pointer;

		&:hover,
		&:active {
			color: ${theme.colors.PURPLE_500};
		}
	`}
`;
