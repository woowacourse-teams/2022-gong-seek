import styled from '@emotion/styled';

export const UserProfile = styled.img`
	width: ${({ theme }) => theme.size.SIZE_032};
	height: ${({ theme }) => theme.size.SIZE_032};

	border-radius: 50%;

	object-fit: cover;
	object-position: center;
	box-shadow: 0 0 2px ${({ theme }) => theme.colors.BLACK_500};
	cursor: pointer;
`;

export const Container = styled.div`
	position: relative;
`;

export const SrOnlyContainer = styled.div`
	position: absolute;
	width: 1px;
	height: 1px;
	margin: -1px;
	overflow: hidden;
	clip-path: polygon(0 0, 0 0, 0 0);
`;
