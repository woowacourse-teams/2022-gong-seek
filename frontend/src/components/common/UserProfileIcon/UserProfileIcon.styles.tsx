import styled from '@emotion/styled';

export const UserProfile = styled.img`
	width: ${({ theme }) => theme.size.SIZE_035};
	height: ${({ theme }) => theme.size.SIZE_035};

	border-radius: 50%;

	object-fit: cover;
	object-position: center;
	box-shadow: 0 0 2px ${({ theme }) => theme.colors.BLACK_500};
	cursor: pointer;
`;

export const Container = styled.div`
	position: relative;
	margin: 0 ${({ theme }) => theme.size.SIZE_160} 0 auto;
`;
